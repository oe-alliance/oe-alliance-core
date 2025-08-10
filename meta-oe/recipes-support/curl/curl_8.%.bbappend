PACKAGECONFIG:class-target = "${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)} basic-auth bearer-auth digest-auth negotiate-auth libidn openssl proxy threaded-resolver verbose zlib"

PV = "8.15.0"
SRC_URI[sha256sum] = "6cd0a8a5b126ddfda61c94dc2c3fc53481ba7a35461cf7c5ab66aa9d6775b609"
