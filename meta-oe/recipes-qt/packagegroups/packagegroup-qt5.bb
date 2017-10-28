SUMMARY = "Qt5 for Embedded Linux (Qt without X11)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"


inherit packagegroup

RDEPENDS_${PN} = " \
	qtbase \
	qtbase-plugins \
	qtbase-tools \
	qtdeclarative \
	qtdeclarative-tools \
	qtdeclarative-qmlplugins \
	qtmultimedia \
	qtmultimedia-plugins \
	qtmultimedia-qmlplugins \
	qtsvg \
	qtsvg-plugins \
	qtsensors \
	qtimageformats \
	qtimageformats-plugins \
	qtsystems \
	qtsystems-tools \
	qtsystems-qmlplugins \
	qtscript \
	qt3d \
	qt3d-qmlplugins \
	qtwebkit \
	qtwebkit-examples-examples \
	qtwebkit-qmlplugins \
	qtgraphicaleffects-qmlplugins \
	qtconnectivity-qmlplugins \
	qtlocation-plugins \
	qtlocation-qmlplugins \
	cinematicexperience \
	qtserialbus \
	"