package modulo;

import com.intuit.karate.junit5.Karate;

public class ApiRunner {

    @Karate.Test
    Karate testUserEndpoint() {
        return Karate.run("user").relativeTo(getClass());
    }

    @Karate.Test
    Karate testErrors(){
        return Karate.run("error").relativeTo(getClass());
    }

    @Karate.Test
    Karate testDeletion(){
        return Karate.run("deletion").relativeTo(getClass());
    }
}
