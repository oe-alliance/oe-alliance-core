# Drops packages the feed does not carry, so they are never written as an ipk and
# the feed needs no post-processing for deleting them again and regenerating the
# indexes afterwards.
#
#   OEA_PACKAGE_DROP  glob patterns of package names to drop
#   OEA_PACKAGE_KEEP  glob patterns kept despite matching OEA_PACKAGE_DROP. Use the
#                     name the recipe gives the package, before debian.bbclass
#                     renames it - glibc-dbg, not libc6-dbg.
#
# Example for site.conf:
#
#   INHERIT += "oea-package-drop"
#   OEA_PACKAGE_DROP = "*-src *-doc *-dbg *-po *-tests *-testsuite *-examples"
#   OEA_PACKAGE_KEEP = "enigma2-src enigma2-dbg enigma2-locale-*-dbg glibc-dbg \
#                       enigma2-plugin-extensions-lcd4linux-src"
#
# The drop is appended to read_subpackage_metadata, which restores PACKAGES from
# the pkgdata before the packages are written. do_package itself never calls it and
# keeps its signature, so editing the lists only rewrites the ipks instead of
# rebuilding from source.
#
# Matching is on the package name rather than on ${PN}, because recipes name their
# subpackages freely: e.g. libpcre produces pcregrep-doc, and enigma2 produces the
# built-in plugins as enigma2-plugin-*.

OEA_PACKAGE_DROP ?= ""
OEA_PACKAGE_KEEP ?= ""

def oea_package_drop_match(pkg, d):
    import fnmatch

    drop = (d.getVar('OEA_PACKAGE_DROP') or '').split()
    if not any(fnmatch.fnmatchcase(pkg, p) for p in drop):
        return False

    keep = (d.getVar('OEA_PACKAGE_KEEP') or '').split()
    return not any(fnmatch.fnmatchcase(pkg, p) for p in keep)

# Extracting debug info is pointless for a -dbg package that gets dropped, but the
# kept ones have to be filled, so this follows the lists instead of being a switch
# of its own. vardepvalue pins the signature to the resulting 0 or 1, so editing
# the lists only rebuilds recipes whose answer actually changes.
INHIBIT_PACKAGE_DEBUG_SPLIT ?= "${@'1' if oea_package_drop_match(d.getVar('PN') + '-dbg', d) else '0'}"
INHIBIT_PACKAGE_DEBUG_SPLIT[vardepvalue] = "${INHIBIT_PACKAGE_DEBUG_SPLIT}"

python read_subpackage_metadata:append () {
    kept = []

    for pkg in (d.getVar('PACKAGES') or '').split():
        if oea_package_drop_match(pkg, d):
            bb.note('oea-package-drop: dropped %s' % pkg)
        else:
            kept.append(pkg)

    d.setVar('PACKAGES', ' '.join(kept))
}
