include python3-package-split.inc

do_configure:append() {
	rust_ver=$(${STAGING_BINDIR_NATIVE}/rustc --version | awk '{print $2}')
	sed -i "$ a _rustVersion_ = \"$rust_ver\"" ${S}/src/bcrypt/__init__.py
}
