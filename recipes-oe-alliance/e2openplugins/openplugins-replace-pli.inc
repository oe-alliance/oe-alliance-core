# Open version should replace old PLi version of the same plugin. This
# include file arranges that the Open version is newer, and that the
# old PLi plugin is removed on install.
OLDPKG = "${@bb.data.getVar('PN',d,1).replace('-extensions-','-pli-')}"
RREPLACES_${PN} = "${OLDPKG}"
RCONFLICTS_${PN} = "${OLDPKG}"

inherit gitpkgv
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r0"
