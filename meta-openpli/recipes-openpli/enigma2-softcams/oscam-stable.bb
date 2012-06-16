SRCREV = "5741"
MODULE = "1.10"
OSCAMBIN = "oscam-stable"
DEPENDS = "openssl"
RDEPENDS_${PN} = "openssl oscam-util-list-smargo"
URI = "svn://oscam.to/svn/oscam/tags;module=1.10;proto=http;scmdata=keep;rev=${SRCREV}"
SSL = "-DWITH_SSL=1"
PCSC = "-DHAVE_PCSC=0"
LIBUSB = "-DHAVE_LIBUSB=1"
ALTERNATIVE_PRIORITY = "10"
require oscam-bin.inc

LIC_FILES_CHKSUM = "file://LICENCE;md5=d32239bcb673463ab874e80d47fae504"
