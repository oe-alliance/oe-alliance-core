# The list of packages that should have xinetd configurations added.  For
# each entry, optionally have a XINETD_SERVICE_[package] that lists the service
# names in this package.  If this variable isn't set, [package] is used.
XINETD_PACKAGES ?= "${PN}"

# This class will be included in any recipe that supports xinetd,
# even if the xinetd DISTRO_FEATURE isn't enabled. As such don't
# make any changes directly but check the DISTRO_FEATURES first.

def xinetd_enabled(d):
    distro_features = d.getVar('DISTRO_FEATURES', True).split()
    return 'xinetd' in distro_features and 'sysvinit' in distro_features

python __anonymous() {
    if xinetd_enabled(d):
        d.appendVar("DEPENDS", " xinetd")
}

do_install_append() {
	if ${@['false', 'true'][xinetd_enabled(d)]}; then
		install -d ${D}${sysconfdir}/xinetd.d
		for srcfile in ${WORKDIR}/*.xinetd.in; do
			dstfile=`basename $srcfile .xinetd.in`
			sed -e 's,@BINDIR@,${bindir},' \
			    -e 's,@SBINDIR@,${sbindir},' \
			    $srcfile > ${D}${sysconfdir}/xinetd.d/$dstfile
			chmod 644 ${D}${sysconfdir}/xinetd.d/$dstfile
		done
	fi
}

xinetd_reload() {
[ -z "$D" ] && PID=`pidof xinetd` && kill -HUP $PID || true
}

python xinetd_populate_packages() {
    if not xinetd_enabled(d):
        return

    def package_get_var(pkg, var):
        val = (d.getVar('%s_%s' % (var, pkg), True) or "").strip()
        if val == "":
            val = (d.getVar(var, True) or "").strip()
        return val

    def package_append_script(pkg, script_type, shell_function):
        script = package_get_var(pkg, 'pkg_%s' % script_type) or '#!/bin/sh\n'
        if not script.endswith('\n'):
            script += '\n'
        script += d.getVar(shell_function, True)
        if not script.endswith('\n'):
            script += '\n'
        d.setVar('pkg_%s_%s' % (script_type, pkg), script)

    def xinetd_check_package(pkg):
        packages = d.getVar('PACKAGES', True).split()
        if not pkg in packages:
            bb.error('%s does not appear in package list, please add it' % pkg)

    def xinetd_add_rrecommends(pkg):
        bb.note("adding xinetd dependency to %s" % pkg)

        rrecommends = (d.getVar('RRECOMMENDS_%s' % pkg, True) or "").split()
        if not 'xinetd' in rrecommends:
            rrecommends.append('xinetd')
        d.setVar('RRECOMMENDS_%s' % pkg, ' '.join(rrecommends))

    def xinetd_generate_package_scripts(pkg):
        bb.note('adding xinetd postinst and postrm scripts to %s' % pkg)
        package_append_script(pkg, 'postinst', 'xinetd_reload')
        package_append_script(pkg, 'postrm', 'xinetd_reload')

    def xinetd_append_file(pkg, file_append):
        if os.path.exists(oe.path.join(d.getVar("D", True), file_append)):
            var_name = 'FILES_%s' % pkg
            files = (d.getVar(var_name, False) or "").split()
            if file_append not in files:
                files.append(file_append)
                d.setVar(var_name, ' '.join(files))

    def xinetd_check_services():
        path = oe.path.join(d.getVar('sysconfdir', True), 'xinetd.d')
        for pkg in d.getVar('XINETD_PACKAGES', True).split():
            services = package_get_var(pkg, 'XINETD_SERVICE').split()
            if len(services) == 0:
                services.append(pkg)
            for service in services:
                xinetd_append_file(pkg, oe.path.join(path, service))

    # run all modifications once when creating package
    if os.path.exists(d.getVar("D", True)):
        for pkg in d.getVar('XINETD_PACKAGES', True).split():
            xinetd_check_package(pkg)
            xinetd_generate_package_scripts(pkg)
            xinetd_add_rrecommends(pkg)
        xinetd_check_services()
}

PACKAGESPLITFUNCS_prepend = "xinetd_populate_packages "
