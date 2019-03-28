module.exports = {
    apps : [{
        name: 'Javaminion',
        script: '/usr/bin/java',
        cwd: '/root/archives/',
        // Options reference: https://pm2.io/doc/en/runtime/reference/ecosystem-file/
        args: [
            '-XX:ErrorFile=/var/log/java/java_error%p.log',
            '-Xmx1500m',
            '-jar',
            '/root/archives/dominionweb-1.0-SNAPSHOT.jar'
        ],
        instances: 1,
        autorestart: true,
        watch: true,
        max_memory_restart: '1500M',
        exec_interpreter: 'none',
        exec_mode: 'fork',
        env: {
            NODE_ENV: 'development'
        },
        env_production: {
            NODE_ENV: 'production'
        }
    }]
};