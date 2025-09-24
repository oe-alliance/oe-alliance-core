NAME = "SinriConnect"
SUMMARY = "Control your Receiver with Alexa or Google Home"
SECTION = "extra"
PRIORITY = "optional"
HOMEPAGE = "http://openspa.info"

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

DEPENDS += "${PYTHON_PN}"
RDEPENDS:${PN} += "${PYTHON_PN}-sinricpro"

PV = "1.0+git"
FOLDER = "sinriconnect"

S = "${WORKDIR}/${P}"
UNPACKDIR = "${S}"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${NAME}"

do_install() {
    install -d ${D}${PLUGINPATH}
    cp -r ${S}/git/${FOLDER}/plugin/* ${D}${PLUGINPATH}
}

do_install:append() {
    find ${D}${PLUGINPATH} -name "*.py" -delete
}

pkg_postinst:${PN}() {
#!/bin/sh

if [ ! -d /etc/keys/ ]; then
    mkdir /etc/keys
fi

if [ ! -f /etc/keys/sinric.keys ]; then
    echo "APP_KEY=" >> /etc/keys/sinric.keys
    echo "APP_SECRET=" >> /etc/keys/sinric.keys
    echo "TV_ID=" >> /etc/keys/sinric.keys
fi
exit 0
}

FILES:${PN} = "${PLUGINPATH}"

INSANE_SKIP:${PN} = "already-stripped ldflags"

addtask translate after do_configure before do_compile
