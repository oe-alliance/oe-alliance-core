PV = "0.37.1"
SRC_URI[sha256sum] = "a99b7262525a454d1065cf76dd17240fd808dfc4ef15636990ff83a5d0d9e740"

PACKAGECONFIG = "expat openssl libproxy webdav zlib"
PACKAGECONFIG:class-native = "expat openssl webdav zlib"

PACKAGE_NO_LOCALE = "1"
