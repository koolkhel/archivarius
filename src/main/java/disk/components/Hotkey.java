package disk.components;

import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.BindingConstants;
import org.slf4j.Logger;

/**
 * Created by
 * User: yury
 * at
 * Date: 03.05.2009
 * Time: 0:58:02
 *
 * нужен для задания горячих клавиш. По идее, должно
 * работать как задание пар "клавиша-адресат". Но
 * смутно хочется, чтобы, например, ctrl-enter работал
 * в любой форме, которая хочется. То ли для этого
 * запоминать, в какой сейчас форме, то ли для каждой формы
 * делать хоткей, соответствующий кнопке субмит формы.
 *
 * Основным моментом пока что является то, что действие
 * нужно не любое, а только клики на линки. Т.е. линки +
 * кнопки формы. Что слегка упрощает задачу
 */
@IncludeJavaScriptLibrary("shortcut.js")
public class Hotkey {
    @Inject
    private Logger logger;

    @Environmental
    private RenderSupport renderSupport;

    /**
     * id html контрола, на который привязывается событие
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String control;

    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "false")
    private boolean global;

    /**
     * сочетание клавишь в формате hotkey.js, вызывающее данное событие
     */
    @Parameter(required=true, allowNull=false, defaultPrefix = BindingConstants.LITERAL)
    private String key;

    /**
     * что нужно сделать по горячему сочетанию клавиш
     */
    @Parameter(required=true, allowNull=false, defaultPrefix = BindingConstants.LITERAL)
    private String action;
    
    public void setupRender() {

    }

    public void beginRender() {

    }

    public void afterRender() {
        // типа да
        String a = "hello";
        if (global) {
            // типа global == вся страница
            renderSupport.addScript("shortcut.add(\"" + key + "\", " +
                "function() {" +
                action +
                "}, {" +
                "'type': 'keydown'," +
                "'propagate': false" +
                "});");   
        } else {
            renderSupport.addScript("shortcut.add(\"" + key + "\", " +
                "function() {" +
                action +
                "}, {" +
                "'type': 'keydown'," +
                "'propagate': false," +
                "'target': $('" + control + "')});");
        }
    }
}
