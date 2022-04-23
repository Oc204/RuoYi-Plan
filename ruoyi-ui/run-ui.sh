#!/bin/sh

## 安装依赖
npm install --registry=https://registry.npm.taobao.org

## 打包
npm run build:prod

## 移动到指定文件夹让nginx读取
\cp -r dist /home/project/ruo-yi-plan-ui
