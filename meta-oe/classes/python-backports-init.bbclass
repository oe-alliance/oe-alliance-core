inherit python-dir

RDEPENDS_${PN} += "python-backports-init"

do_install_prepend() {
    rm -rf $(find . -path "*/backports/__init__.py" -type f)
}
