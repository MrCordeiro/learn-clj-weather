# Weather - a Clojure toy project

Toy project to learn ClojureScript.

## Overview

Mini weather applet with a form that make requests to an API.

## Development

To get an interactive development environment run:

```shell
clj -A:fig:build
```

This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

```clj
(js/alert "Am I connected?")
```

and you should see an alert in the browser window.

To clean all compiled files:

```shell
rm -rf target/public
```

To create a production build run:

```shell
rm -rf target/public
clojure -A:fig:min
```

## Learning notes

The project was created with the `clj-new` package by running:

```shell
$ clj -X:new :template figwheel-main :name MrCordeiro/weather :args '["+deps" "--reagent"]'
```
