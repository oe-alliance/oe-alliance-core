DESCRIPTON = "Emacs Without X for odroid-c2"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r0"

SRC_URI = "file://odroid-c2-emacs.tar.gz"

S = "${WORKDIR}"
FILES_${PN}="${prefix}"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
PACKAGES= "${PN}"

PR = "r0"

RDEPENDS_${PN} = "bash perl cairo gtk+ libxft libsm libpng libice gtk+ tiff libxml2"

do_install() {
    #
    # Create directories:
    #   ${D}${sysconfdir}/init.d - will hold the scripts
    #   ${D}${sysconfdir}/rcS.d  - will contain a link to the script that runs at startup
    #   ${D}${sysconfdir}/rc5.d  - will contain a link to the script that runs at runlevel=5
    #   ${D}${sbindir}           - scripts called by the above
    #
    # ${D} is effectively the root directory of the target system.
    # ${D}${sysconfdir} is where system configuration files are to be stored (e.g. /etc).
    # ${D}${sbindir} is where executable files are to be stored (e.g. /sbin).
    #
    install -d ${D}${prefix}/
  
    #
    # Install files in to the image
    #
    # The files fetched via SRC_URI (above) will be in ${WORKDIR}.
    #
    #tar zxvf ${WORKDIR}/odroid-c2-emacs  ${D}${prefix}/
    cp -r ${WORKDIR}/local ${D}${prefix}/

    #
    # Symbolic links can also be installed. e.g.
    #
    # ln -s support-script-link ${D}${sbindir}/support-script

    #
    # Create symbolic links from the runlevel directories to the script files.
    # Links of the form S... and K... mean the script when be called when
    #
    #ln -sf ../init.d/odroid-c2-init  ${D}${sysconfdir}/rcS.d/S90odroid-c2-init
}



