function fn() {
    // Load the karate.env system property
    var env = karate.env;
    karate.log('karate.env system property was:', env);

    // Set a reasonable default if env isn't set
    if (!env) {
        env = 'local';
    }
    // Base config JSON for 'local'
    var config = {
        base_url: 'http://localhost:8080',
        base_path: '/modulo/api/user/',
        app_id: 'modulo',
    };

    if (env == 'dev') {
        // Override specific properties that change by env
        config.base_url = 'https://dev-host';
    }
    else if (env == 'staging') {
        config.base_url = 'https://staging-host';
    }

    karate.configure('connectTimeout', 5000);
    karate.configure('readTimeout', 5000);

    return config;
}