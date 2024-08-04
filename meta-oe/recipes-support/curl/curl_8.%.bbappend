PACKAGECONFIG:class-target = "${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)} basic-auth bearer-auth digest-auth negotiate-auth libidn openssl proxy random threaded-resolver verbose zlib"
