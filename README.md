# aerogear-controller POC

This is a **proof-of-concept** on having a very simple mvc controller built on top of resteasy.

## how it works

Just by returning a `View` instance, you can trigger page dispatch:

    @Path("/welcome")
    @GET
    public View anyMethodName() {
        return new View("/WEB-INF/pages/somepage.jsp");
    }
    
    
You can pass "model" objects to the view as well:

    return new View("…/.jsp", new Car());
    
Then access it from the JSP page, for example:

    <h1>${car}</h1>



### credits / attribution

mildly inspired by htmleasy and has some code extracted from VRaptor.