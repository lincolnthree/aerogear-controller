# aerogear-controller PoC

## how to create a new project

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
        
1. voilà!
