MODULE = "1.10"
OSCAMBIN = "oscam-unstable"
OSCAMDEPENS = "openssl"
OSCAMRDEPENDS ="openssl"
URI = "svn://oscam.to/svn/oscam/tags;module=1.10;proto=http;scmdata=keep"
SSL = "-DWITH_SSL=1"
PCSC = "-DHAVE_PCSC=0"
LIBUSB = "-DHAVE_LIBUSB=1"
ALTERNATIVE_PRIORITY = "10"
require oscam-bin.inc

LIC_FILES_CHKSUM = "file://LICENCE;md5=d32239bcb673463ab874e80d47fae504"
