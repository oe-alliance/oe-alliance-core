SUMMARY = "wolfSSL Lightweight Embedded SSL/TLS Library"
DESCRIPTION = "wolfSSL is a lightweight SSL/TLS library written in C and \
               optimized for embedded and RTOS environments. It can be up \
               to 20 times smaller than OpenSSL while still supporting \
               a full TLS client and server, up to TLS 1.3"
HOMEPAGE = "https://www.wolfssl.com/products/wolfssl"
BUGTRACKER = "https://github.com/wolfssl/wolfssl/issues"
SECTION = "libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PROVIDES += "cyassl"
RPROVIDES_${PN} = "cyassl"
PROVIDES += "wolfssl"
RPROVIDES_${PN} = "wolfssl"

SRC_URI[md5sum] = "5f4a3cd0aea77a6ae507547fc864505c"
SRC_URI[sha256sum] = "59edfb6b70c17c82f2ef6126198549adf6cbccee8f013cfca88323590f8cbd43"
SRC_URI = "https://www.wolfssl.com/wolfssl-${PV}.zip"

inherit autotools

EXTRA_OECONF += "--enable-opensslextra \
                 --enable-tls13"

BBCLASSEXTEND += "native nativesdk"
