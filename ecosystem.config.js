module.exports = {
    apps : [{
        name: 'Javaminion',
        script: '/usr/bin/java',
        cwd: '/root/archives/',
        // Options reference: https://pm2.io/doc/en/runtime/reference/ecosystem-file/
        args: [

            '-Xms100m',
            '-Xmx500m',
            '-XX:+UseSerialGC',
            '-jar',
            '/root/archives/dominionweb-1.0-SNAPSHOT.jar'
        ],
        instances: 1,
        autorestart: true,
        watch: false,
        exec_interpreter: 'none',
        exec_mode: 'fork',
        env: {
            SPRING_PROFILES_ACTIVE: 'prod'
        }
    }]
};