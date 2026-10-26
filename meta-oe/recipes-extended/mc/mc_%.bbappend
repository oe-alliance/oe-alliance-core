# save ~7 MB, base terminfo is in /etc/terminfo
RRECOMMENDS:${PN}:remove = "ncurses-terminfo"

# mc scans extfs.d even when the optional helper packages are not installed.
# Add the empty directory after splitting so the helpers stay in their packages.
PACKAGESPLITFUNCS:append = " mc_package_extfs_dir"

mc_package_extfs_dir() {
    install -d ${PKGDEST}/${PN}${libexecdir}/mc/extfs.d
}
