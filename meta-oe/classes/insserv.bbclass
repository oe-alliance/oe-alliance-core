# insserv.bbclass
#
# This class appends postinst script to enable SysV initscripts on boot.
# Because command "update-rc.d" cannot change root path,
# so we use command "insserv" instead.
#
# INITSCRIPT_PACKAGES   - list of packages contains scripts which we want to enable.
# INITSCRIPT_NAMES_pkg  - list of scripts for "pkg".
# INITSCRIPT_PARAMS_pkg - params to set runlevel
#                         Ex: ",start=2,3,4,5,stop=0,1,6"
#

DEPENDS_append_class-target = " insserv-native"

INITSCRIPT_PACKAGES ?= "${PN}"
INITSCRIPT_NAMES ?= ""
INITSCRIPT_PARAMS ?= ""

INIT_D_DIR = "${sysconfdir}/init.d"

insserv_postinst() {
# Enable service on boot
if [ "${INITSCRIPT_NAMES}" != "" ]; then
  for script in ${INITSCRIPT_NAMES}; do
    if [ -f "$D${INIT_D_DIR}/${script}" ]; then
      insserv -p $D${INIT_D_DIR} -f ${script}${INITSCRIPT_PARAMS}
      #insserv -p $D${INIT_D_DIR} -c $D${sysconfdir}/insserv.conf -o $D${sysconfdir}/insserv/overrides
    fi
  done
fi
}

insserv_prerm() {
# Remove service from all runlevels
if [ "${INITSCRIPT_NAMES}" != "" ]; then
  for script in ${INITSCRIPT_NAMES}; do
    if [ -f "$D${INIT_D_DIR}/${script}" ]; then
      insserv -p $D${INIT_D_DIR} -f -r ${script}
    fi
  done
fi
}

PACKAGESPLITFUNCS_prepend = "insserv_populate_packages "
PACKAGESPLITFUNCS_remove_class-nativesdk = "insserv_populate_packages "

insserv_populate_packages[vardeps] += "insserv_postinst"
insserv_populate_packages[vardepsexclude] += "OVERRIDES"

python insserv_populate_packages() {
    def insserv_package(pkg):
        # Add pkg to overrides so that it finds the INITSCRIPT_NAMES_pkg variable
        localdata = d.createCopy()
        localdata.prependVar("OVERRIDES", pkg + ":")
        bb.data.update_data(localdata)

        postinst = d.getVar('pkg_postinst_%s' % pkg, True)
        if not postinst:
            postinst = '#!/bin/sh\n'
        postinst += localdata.getVar('insserv_postinst', True)
        d.setVar('pkg_postinst_%s' % pkg, postinst)

        prerm = d.getVar('pkg_prerm_%s' % pkg, True)
        if not prerm:
            prerm = '#!/bin/sh\n'
        prerm += localdata.getVar('insserv_prerm', True)
        d.setVar('pkg_prerm_%s' % pkg, prerm)


    pkgs = d.getVar('INITSCRIPT_PACKAGES', True)
    if pkgs != None:
        for pkg in pkgs.split():
            insserv_package(pkg)
}
