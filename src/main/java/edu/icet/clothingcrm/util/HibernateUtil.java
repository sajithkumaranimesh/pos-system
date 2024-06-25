//package edu.icet.clothingcrm.util;
//
//import edu.icet.clothingcrm.entity.CategoryEntity;
//import edu.icet.clothingcrm.entity.EmployeeEntity;
//import edu.icet.clothingcrm.entity.ProductEntity;
//import edu.icet.clothingcrm.entity.SupplierEntity;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//
//public class HibernateUtil {
//    private static SessionFactory session = createSession();
//
//    private static SessionFactory createSession() {
//        StandardServiceRegistry build = new StandardServiceRegistryBuilder()
//                .configure("hibernate.cfg.xml")
//                .build();
//
//        Metadata metadata = new MetadataSources(build)
//                .addAnnotatedClass(CategoryEntity.class)
//                .addAnnotatedClass(EmployeeEntity.class)
//                .addAnnotatedClass(ProductEntity.class)
//                .addAnnotatedClass(SupplierEntity.class)
//                .getMetadataBuilder()
//                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
//                .build();
//
//        return metadata.getSessionFactoryBuilder().build();
//    }
//
//    public static Session getSession(){
//        return session.openSession();
//    }
//}
