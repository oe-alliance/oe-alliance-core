tcl_package_preprocess_sh4() {
    sed -i -e "s;-L${STAGING_LIBDIR};-L${libdir};g" \
           -e "s;${STAGING_INCDIR};${includedir};g" \
           -e "s;--sysroot=${RECIPE_SYSROOT};;g" \
           ${PKGD}${libdir}/tclConfig.sh

    rm -f ${PKGD}${bindir_crossscripts}/tclConfig.sh
}
