FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://inputrc \
            file://bashrc \
            file://profile \
           "

do_install_append() {
	install -d ${D}${ROOT_HOME}
        install -m 0700 ${WORKDIR}/bashrc  ${D}${ROOT_HOME}/.bashrc
        install -m 0700 ${WORKDIR}/inputrc ${D}${ROOT_HOME}/.inputrc
        install -m 0700 ${WORKDIR}/profile ${D}${ROOT_HOME}/.profile
}

FILES_${PN} += "${ROOT_HOME}/.bashrc ${ROOT_HOME}/.inputrc ${ROOT_HOME}/.profile"
CONFFILES_${PN} += "${ROOT_HOME}/.bashrc ${ROOT_HOME}/.inputrc ${ROOT_HOME}/.profile"
