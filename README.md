# Spring ORM without XML
Небольшой пример использования Spring+ORM без xml-файлов

Создается HSQL-база в оперативной памяти, в нее мапится сущность (таблица) Person, сохраняются два экземпляра (insert) и выводятся в консоль (select).

## Конфигурация спринг-приложения

```
@Configuration
@EnableTransactionManagement
public class SpringConfigContext {

    @Bean
    App mockApp() { return new App(); }

    @Bean
    public DataSource mockDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .build();
        return db;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mockEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(mockDataSource());
        entityManager.setPersistenceUnitName("myPersistence");
        entityManager.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManager.setPackagesToScan("com.raukhvarger.examples.spring_webapp.db.entity");
        HibernateJpaVendorAdapter hibJpa = new HibernateJpaVendorAdapter();
        hibJpa.setGenerateDdl(true);
        hibJpa.setShowSql(true);
        hibJpa.setDatabase(Database.HSQL);
        entityManager.setJpaVendorAdapter(hibJpa);
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        transactionManager.setDataSource(mockDataSource());
        transactionManager.setJpaDialect(new HibernateJpaDialect());
        return transactionManager;
    }

    @Bean
    public DAO mockDAO() {
        return new DAO();
    }

}
```

## DAO-класс

```
@Component
public class DAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(Person person) {
        em.persist(person);
    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return em.createQuery("SELECT p FROM Person p").getResultList();
    }

}
```

## Entity-класс

```
@Entity(name = "Person")
public class Person {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    public Integer age;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Person() {}

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}
```

## Пример использования

```
@Component
public class App {

    @Autowired
    DAO dao;

    public static void main(String args[]) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigContext.class);

        App app = context.getBean(App.class);

        app.dao.persist(new Person("Ivan Petrov", 25));
        app.dao.persist(new Person("Sidor Ivanov", 31));

        pr(app.dao.findAll());
    }

    public static void pr(Object str) {
        System.out.println(str);
    }

}
```
