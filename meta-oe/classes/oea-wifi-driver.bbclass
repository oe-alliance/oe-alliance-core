# Picks which driver a box gets for a wireless chip: the one built into its
# own kernel, or the out-of-tree recipe from this layer.
#
# INTREE_WIFI is read out of the defconfig of the kernel recipe the machine
# actually uses, so it cannot drift away from what the kernel really builds.
# The parse is marked as depending on that file, so bitbake reparses when a
# defconfig changes and the choice follows by itself.
#
# A chip served by more than one in-tree driver takes a list, best driver
# first, and the first one the kernel has wins.
#
# FORCE_OOT_WIFI names out-of-tree recipes that win anyway. It is there for
# the chips where the out-of-tree driver does more than the in-tree one:
# rtl8192eu is the only one of the two that can do WPA3, for instance.

FORCE_OOT_WIFI ?= "rtl8192eu"

WIFI_CONFIG_SYMBOLS = "\
    RTL8XXXU:rtl8xxxu RTL8192CU:rtl8192cu RTL8192CE:rtl8192ce \
    R8188EU:r8188eu R8712U:r8712u RT2800USB:rt2800usb \
    MT7601U:mt7601u MT76x0U:mt76x0u RTL8723BS:rtl8723bs RTW88:rtw88 \
"

def intree_wifi(d):
    import os, re, glob

    provider = d.getVar('PREFERRED_PROVIDER_virtual/kernel') or ''
    version = d.getVar('PREFERRED_VERSION_%s' % provider) or ''
    if not provider:
        return ''

    names = [n for n in (d.getVar('MACHINE'), d.getVar('MACHINEBUILD')) if n]
    found = []
    for layer in (d.getVar('BBLAYERS') or '').split():
        # brand layers sit one level below the collection, hence both shapes
        for base in ('%s/recipes-linux/%s-%s' % (layer, provider, version),
                     '%s/*/recipes-linux/%s-%s' % (layer, provider, version)):
            for name in names:
                found += glob.glob('%s/%s/defconfig' % (base, name))
            found += glob.glob('%s/defconfig' % base)
    if not found:
        return ''

    path = sorted(found)[0]
    bb.parse.mark_dependency(d, path)
    with open(path, encoding='utf-8', errors='ignore') as fd:
        text = fd.read()

    out = []
    for pair in (d.getVar('WIFI_CONFIG_SYMBOLS') or '').split():
        symbol, name = pair.split(':')
        if re.search('^CONFIG_%s=[my]' % symbol, text, re.M):
            out.append(name)
    return ' '.join(sorted(out))

def wifi_driver(d, intree_modules, intree_packages, oot_recipe):
    if oot_recipe in (d.getVar('FORCE_OOT_WIFI') or '').split():
        return oot_recipe
    have = (d.getVar('INTREE_WIFI') or '').split()
    for module, package in zip(intree_modules.split(), intree_packages.split()):
        if module in have:
            return package
    return oot_recipe

INTREE_WIFI = "${@intree_wifi(d)}"
