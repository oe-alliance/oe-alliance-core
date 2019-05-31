FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RDEPENDS_${PN} += "openssl"
RDEPENDS_${PN}_class-target_remove = "openssl-bin"
RDEPENDS_${PN}_class-native_remove = "openssl-native"
RDEPENDS_${PN}_class-nativesdk_remove = "nativesdk-openssl-bin"

SRC_URI += "file://use-c_rehash-openssl10.patch"
