import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

public class MainClass {
    public static void main(String[] args) throws URISyntaxException, MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

        System.out.println("Load jar file: " + args[0]);
        URL resource = Thread.currentThread().getContextClassLoader().getResource(args[0]);

        System.out.println("Get URLClassLoader: " + args[0]);
        URLClassLoader sysLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        Class sysClass = URLClassLoader.class;
        Method sysMethod = sysClass.getDeclaredMethod("addURL",new Class[] {URL.class});
        sysMethod.setAccessible(true);

        System.out.println("Add jar file to ClassLoader: "+ args[0]);
        sysMethod.invoke(sysLoader, new Object[]{resource.toURI().toURL()});

        System.out.println("Loading class: " + args[1]);
        Class MyClass = sysLoader.loadClass(args[1]);

        System.out.println("Found class methods: ");
        for (Method method : MyClass.getDeclaredMethods()) {
            final StringBuffer sb = new StringBuffer();
            sb.append(MyClass.getName() + ".");
            sb.append(method.getName()+ "( ");
            for (Type type : method.getParameterTypes()) {
                sb.append(" " + type.toString());
            }
            sb.append(" )");
            System.out.println(sb.toString());
        }

        System.out.println("Instantiating method: " + args[2]);
        Method printMeMethod = MyClass.getMethod(args[2], new Class[]{});

        System.out.println("Creating new instance of class: " + args[1]);
        Object MyClassObj = MyClass.newInstance();

        System.out.println("Executing method: " + args[2]);
        printMeMethod.invoke(MyClassObj);
    }
}
