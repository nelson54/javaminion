#!/usr/bin/env bash
NODE_PROJECT_PATH="./src/main/node"
PUBLIC_RESOURCES_PATH="./src/main/resources/public"

npm install --cwd ${NODE_PROJECT_PATH} --prefix ${NODE_PROJECT_PATH}-Rf
npm run build --prefix ${NODE_PROJECT_PATH}

mkdir -p ${PUBLIC_RESOURCES_PATH} && rm -rf ${PUBLIC_RESOURCES_PATH}/*

cp -r ${NODE_PROJECT_PATH}/src/assets ${PUBLIC_RESOURCES_PATH}
cp -r ${NODE_PROJECT_PATH}/dist/* ${PUBLIC_RESOURCES_PATH}


