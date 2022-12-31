python debian_package_name_hook:append() {
    import pathlib

    whitelist = set({
        pathlib.PurePath(d.getVar('base_libdir')),
        pathlib.PurePath(d.getVar('libdir')),
    })

    pn = d.getVar('PN')
    mlprefix = d.getVar('MLPREFIX')
    package_arch = d.getVar('PACKAGE_ARCH')
    is_kernel = bb.data.inherits_class('kernel', d) or bb.data.inherits_class('module-base', d)

    for orig_pkg in packages.split():
        # Avoid MLPREFIX in target packages
        #if mlprefix and mlprefix != "nativesdk-":
        #    pkg = d.getVar('PKG_' + orig_pkg) or orig_pkg
        #    if pkg.startswith(mlprefix):
        #        newpkg = pkg[len(mlprefix):]
        #        bb.note("opendreambox-multilib: renaming %s from %s to %s" % (orig_pkg, pkg, newpkg))
        #        d.setVar('PKG_' + orig_pkg, newpkg)
        #        add_rprovides(orig_pkg, d)

        # Generate Multi-Arch flags
        if not d.getVar('MULTI_ARCH_' + orig_pkg) and mlprefix != "nativesdk-":
            arch = d.getVar('PACKAGE_ARCH_' + orig_pkg) or package_arch
            if arch == 'all' or is_kernel:
                flag = 'foreign'
            else:
                conffiles = set({pathlib.PurePath(p) for p in (d.getVar('CONFFILES_' + orig_pkg) or '').split()})
                if conffiles:
                    bb.note("opendreambox-multilib: conffiles=%s" % conffiles)

                # Consider empty packages harmless.
                is_harmless = True
                for f in pkgfiles[orig_pkg]:
                    pkgpath = pathlib.PurePath(pkgdest, orig_pkg)
                    path = pathlib.PurePath("/") / pathlib.PurePath(f).relative_to(pkgpath)
                    bb.note("opendreambox-multilib: path=%s" % path)
                    is_harmless = path in whitelist or path in conffiles or set(path.parents) & whitelist or pn in path.parts
                    if not is_harmless:
                        break

                if is_harmless:
                    flag = 'same'
                else:
                    flag = 'no'

            bb.note("opendreambox-multilib: setting 'Multi-Arch: %s' for %s" % (flag, orig_pkg))
            d.setVar('MULTI_ARCH_%s' % orig_pkg, flag)
}
