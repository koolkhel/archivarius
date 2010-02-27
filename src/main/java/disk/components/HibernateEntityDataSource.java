package disk.components;

import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;

/**
 * Grid data source that queries hibernate. It caches count and results, and is
 * not suitable for reuse.
 */
public class HibernateEntityDataSource<T> implements GridDataSource {

	private final Class<T> persistentClass;

	@Inject
	private Logger logger;

	private Session _session;
	private final Criteria criteria;

	/** cached data */
	protected List<T> data;
	/** count value cached on first call to {@link #getAvailableRows()} */
	protected int count = -1;
	/**
	 * we will select only part of the table so later when asked for a row, we
	 * use this to offset the index
	 */
	protected int startIndex = 0;
	private ComponentResources _resources;

	/**
	 * A datasource for grid. Provide {@link ComponentResources} and
	 * "sortColumn" event will be triggered from the
	 * {@link #prepare(int, int, PropertyModel, boolean)} method.
	 */
	@SuppressWarnings("unchecked")
	public HibernateEntityDataSource(Session session, Class<T> type,
			ComponentResources resources) {
		super();
		persistentClass = type;
		_resources = resources;

		_session = session;
		criteria = _session.createCriteria(persistentClass);

	}

	public static final <T> HibernateEntityDataSource<T> create(
			Session session, Class<T> type, ComponentResources resources) {
		return new HibernateEntityDataSource<T>(session, type, resources);
	}

	/**
	 * this value is cached on first access so do not add filters after it is
	 * called
	 */
	public int getAvailableRows() {
		if (count == -1) {
			criteria.setProjection(Projections.rowCount());
			count = ((Integer) criteria.list().get(0)).intValue();
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		}
		return count;
	}

	public Class<T> getRowType() {
		return persistentClass;
	}

	/**
	 * Add a creterion to filter results. for example:
	 * {@link Restrictions#eq(String, Object)}
	 */
	public void addCriterion(Criterion criterion) {
		criteria.add(criterion);
	}

	public Criteria getCriteria() {
		return criteria;
	}

	/**
	 * The index must be between startIndex and endIndex provided in {@link
	 * #prepare(int, int, List<SortConstraint>)} implemented from {@link
	 * GridDataSource#prepare(int, int, List<SortConstraint>)}
	 */
	public Object getRowValue(int index) {
		return data.get(index - startIndex);
	}

	@SuppressWarnings("unchecked")
	public void prepare(int startIndex, int endIndex,
			List<SortConstraint> sortConstraints) {
		// query is much faster if we take only results we need. it can be up to
		// 10x faster even for a small table with 1000 records
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(endIndex - startIndex + 1);
		this.startIndex = startIndex;

		for (SortConstraint sortConstraint : sortConstraints) {
			String sortColumnName = sortConstraint.getPropertyModel()
					.getPropertyName();
			boolean ascending = (sortConstraint.getColumnSort() == ColumnSort.ASCENDING);
			Order order = ascending ? Order.asc(sortColumnName) : Order
					.desc(sortColumnName);
			criteria.addOrder(order);
			if (_resources != null)
				_resources.triggerEvent("sortColumn", new Object[] { criteria,
						sortColumnName, ascending, persistentClass }, null);
		}

		data = criteria.list();
	}
}
