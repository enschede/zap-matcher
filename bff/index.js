const _ = require('lodash');
const http = require('http');
const httpProxy = require('http-proxy');
const proxy = httpProxy.createProxy();

const hosts = [
    {host: 'local.zzpmatcher.nl', path: 'public', target: 'http://localhost:8080'},
    {host: 'local.zzpmatcher.nl', path: 'user', target: 'http://localhost:8080'},
    {host: 'local.zzpmatcher.nl', path: '', target: 'http://localhost:4200'},
]

const server = http.createServer((req, res) => {
    const host = _.find(hosts, (host) => {
        return req.headers.host === host.host && req.url.startsWith(host.path);
    });
    // TODO handle no host found
    proxy.web(req, res, {target: host.target});
});

server.listen(80);

