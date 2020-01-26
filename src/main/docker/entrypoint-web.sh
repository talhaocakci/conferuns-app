#!/bin/sh

exec npm run webpack-dev-server -- --config webpack/webpack.dev.js --inline --port=9060 --env.stats=minimal
