[![Build Status][build-badge]][build-url]
[![License][license-badge]][license-url]
[![Join the chat at https://gitter.im/ibm-et/spark-kernel][gitter-badge]][gitter-url]
[![Binder](http://mybinder.org/badge.svg)](http://mybinder.org/repo/apache/incubator-toree)

Apache Toree
============
The main goal of the Toree is to provide the foundation for interactive applications to connect to and use [Apache Spark][1]. This
branch supports Apache Spark 1.6+. See master for Spark 2+ support.

Overview
========
Toree provides an interface that allows clients to interact with a Spark Cluster. Clients can send libraries and snippets of code that are interpreted and ran against a preconfigured Spark context. These snippets can do a variety of things:
 1. Define and run spark jobs of all kinds
 2. Collect results from spark and push them to the client
 3. Load necessary dependencies for the running code
 4. Start and monitor a stream
 5. ...

The main supported language is `Scala`, but it is also capable of processing both `Python` and `R`. It implements the latest Jupyter message protocol (5.0), so it can easily plug into the latest releases of Jupyter/IPython (3.2.x+ and 4.x+) for quick, interactive data exploration.

Try It
======
A version of Toree is deployed as part of the [Try Jupyter!][try-jupyter] site. Select `Scala 2.10.4 (Spark 1.4.1)` under the `New` dropdown. Note that this version only supports `Scala`.

Develop
=======
This project uses `make` as the entry point for build, test, and packaging. It supports 2 modes, local and vagrant. The default is local and all command (i.e. sbt) will be ran locally on your machine. This means that you need to
install `sbt`, `jupyter/ipython`, and other development requirements locally on your machine. The 2nd mode uses [Vagrant][vagrant] to simplify the development experience. In vagrant mode, all commands are sent to the vagrant box
that has all necessary dependencies pre-installed. To run in vagrant mode, run `export USE_VAGRANT=true`.  

To build and interact with Toree using Jupyter, run
```
make dev
```

This will start a Jupyter notebook server. Depending on your mode, it will be accessible at `http://localhost:8888` or `http://192.168.44.44:8888`. From here you can create notebooks that use Toree configured for Spark local mode.

Tests can be run by doing `make test`.

>> NOTE: Do not use `sbt` directly.

Build & Package
===============
To build and package up Toree, run
```
make release
```

This results in 2 packages.

- `./dist/toree-<VERSION>-binary-release.tar.gz` is a simple package that contains JAR and executable
- `./dist/toree-<VERSION>.tar.gz` is a `pip` installable package that adds Toree as a Jupyter kernel.

NOTE: `make release` uses `docker`. Please refer to `docker` installation instructions for your system. `USE_VAGRANT` is not supported by this `make` target.

Run Examples
============
To play with the example notebooks, run
```
make jupyter
```

A notebook server will be launched in a `Docker` container with Toree and some other dependencies installed.
Refer to your `Docker` setup for the ip address. The notebook will be at `http://<ip>:8888/`.

Install
=======
PIP packages are hosted on Apache dist. Currently we have a developer preview of 0.1.0.

```
pip install https://dist.apache.org/repos/dist/dev/incubator/toree/0.1.0/snapshots/toree-0.1.0.dev8.tar.gz
jupyter toree install
```

Reporting Issues
================
Refer to and open issue [here][issues]

Communication
=============
You can reach us through [gitter][gitter-url] or our [mailing list][mail-list]

Version
=======
We are working on publishing binary releases of Toree soon. As part of our move into Apache Incubator, Toree will start a new version sequence starting at `0.1`.

Our goal is to keep `master` up to date with the latest version of Spark. When new versions of Spark require specific code changes to Toree, we will branch out older Spark version support.

As it stands, we maintain several branches for legacy versions of Spark. The table below shows what is available now.

Branch                       | Apache Spark Version
---------------------------- | --------------------
[master][master]             | 1.5.1+
[branch-0.1.4][branch-0.1.4] | 1.4.1
[branch-0.1.3][branch-0.1.3] | 1.3.1

Please note that for the most part, new features will mainly be added to the `master` branch.

Building a Release for Apache
=============================

1. Generate the source, binary, and pip distributables via `make release`. Copy the contents
   to the subversion repository `https://dist.apache.org/repos/dist/dev/incubator/toree`
   as a new release candidate for the specified version (e.g. 0.1.0).

2. Publish staging jars to be available on Apache via `GPG_PASSWORD=... make publish-jars`.
   From there, you need to close the open repo to promote to staging. This closing is done
   via the UI here: https://repository.apache.org/#stagingRepositories

3. Create a vote thread similar to [https://lists.apache.org/thread.html/493874de453d9ccbdbc3aecc2f527dea6af82d657104732d726e07f9@<dev.toree.apache.org>](https://lists.apache.org/thread.html/493874de453d9ccbdbc3aecc2f527dea6af82d657104732d726e07f9@<dev.toree.apache.org>)

Resources
=========

We are working on porting our documentation into Apache. For the time being, you can refer to this [Wiki][5] and our [Getting Started][4] guide. You may also visit our [website][website].

[1]: https://spark.apache.org/
[2]: https://github.com/ibm-et/spark-kernel/wiki/Guide-to-the-Comm-API-of-the-Spark-Kernel-and-Spark-Kernel-Client
[3]: https://github.com/ibm-et/spark-kernel/wiki/Guide-to-Developing-Magics-for-the-Spark-Kernel
[4]: https://github.com/ibm-et/spark-kernel/wiki/Getting-Started-with-the-Spark-Kernel
[5]: https://github.com/ibm-et/spark-kernel/wiki

[website]: http://toree.apache.org
[issues]: https://issues.apache.org/jira/browse/TOREE
[build-badge]: https://travis-ci.org/apache/incubator-toree.svg?branch=master
[build-url]: https://travis-ci.org/apache/incubator-toree
[license-badge]: https://img.shields.io/badge/License-Apache%202-blue.svg?style=flat
[license-url]: LICENSE
[gitter-badge]: https://badges.gitter.im/Join%20Chat.svg
[gitter-url]: https://gitter.im/ibm-et/spark-kernel
[try-jupyter]: http://try.jupyter.org
[vagrant]: https://www.vagrantup.com/
[mail-list]: mailto:dev@toree.incubator.apache.org

[master]: https://github.com/apache/incubator-toree
[branch-0.1.4]: https://github.com/apache/incubator-toree/tree/branch-0.1.4
[branch-0.1.3]: https://github.com/apache/incubator-toree/tree/branch-0.1.3
