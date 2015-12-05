import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClass {

    private final static Logger LOG = Logger.getLogger(MainClass.class.getName());

    public static void main(String[] args) throws URISyntaxException, MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        LOG.setLevel(Level.INFO);
        LOG.info("Load jar file: " + args[0]);
        URL resource = Thread.currentThread().getContextClassLoader().getResource(args[0]);

        if (resource == null) {
            LOG.severe("Failed to load jar file : " + args[0]);
            System.exit(1);
        }

        LOG.info("Get URLClassLoader: " + args[0]);
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class sysClass = URLClassLoader.class;
        Method sysMethod = sysClass.getDeclaredMethod("addURL", new Class[]{URL.class});
        sysMethod.setAccessible(true);

        LOG.info("Add jar file to ClassLoader: " + args[0]);
        sysMethod.invoke(sysLoader, new Object[]{resource.toURI().toURL()});

        try {
            LOG.info("Loading class: " + args[1]);
            final Class clazz = sysLoader.loadClass(args[1]);

            LOG.info("Found class methods: ");
            for (Method method : clazz.getDeclaredMethods()) {
                final StringBuffer sb = new StringBuffer();
                sb.append(clazz.getName() + ".");
                sb.append(method.getName() + "( ");
                for (Type type : method.getParameterTypes()) {
                    sb.append(" " + type.toString());
                }
                sb.append(" )");
                LOG.info(sb.toString());
            }

            try {
                LOG.info("Creating new instance of class: " + args[1]);
                Object MyClassObj = clazz.newInstance();

                LOG.info("Instantiating method: " + args[2]);
                Method printMeMethod;
                try {
                    printMeMethod = clazz.getMethod(args[2], new Class[]{});
                    LOG.info("Executing method: " + args[2]);
                    printMeMethod.invoke(MyClassObj);

                } catch (Exception ex) {
                    LOG.severe("Failed to instantiate method: " + args[2]);
                    System.exit(1);
                }
            } catch (Exception ex) {
                LOG.severe("Failed to instantiate class: " + args[1]);
                System.exit(1);
            }
        } catch (Exception ex) {
            LOG.severe("Failed to load class: " + args[1]);
            System.exit(1);
        }
    }
}
