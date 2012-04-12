# aerogear-controller PoC

## how to create a new project

### basic use case
1. add the maven dependency

        <dependency>
            <groupId>org.jboss.aerogear</groupId>
            <artifactId>aerogear-controller</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>

1. create a pojo controller

        public class Home {
            public void index() {
            }
        }

1. create a Java class containing the routes (must extend `AbstractRoutingModule`)

        public class Routes extends AbstractRoutingModule {

        @Override
        public void configuration() {
            route()
                    .from("/")
                    .on(RequestMethod.GET)
                    .to(Home.class).index();
            }
        }

1. create a jsp page at `/WEB-INF/pages/<Controller Class Name>/<method>.jsp`

        <!-- /WEB-INF/pages/Home/index.jsp -->
        <html>
            <body>
                <p>hello from index!</p>
            </body>
        </html>
        
### parameter population

You can use immutable beans straight away as controller parameters:

        public class Store {
            public Car save(Car car) {
                return car;
            }
        }

This can be populated by putting a route to it (preferrably via post, of course)

        route()
            .from("/cars")
            .on(RequestMethod.POST)
            .to(Store.class).save(param(Car.class));


And you can use a simple html form for it, by just following the convention:

            <input type="text" name="car.color"/>
            <input type="text" name="car.brand"/>

The car object will be automatically populated with the provided values - note that it supports deep linking, so this would work fine too:

            <input type="text" name="car.brand.owner"/>

All the intermediate objects are created automatically.

---
you can find a slightly better example at <https://github.com/aerogear/aerogear-controller/tree/routes/aerogear-controller-demo> 