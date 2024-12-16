package reapaso.ec4.repasoEC4.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import reapaso.ec4.repasoEC4.controller.HeroController;
import reapaso.ec4.repasoEC4.security.BasicAuthFilter;

@Configuration
public class ServerConfig extends ResourceConfig {
    public ServerConfig() {
        register(HeroController.class);
        register(BasicAuthFilter.class);
    }
}
