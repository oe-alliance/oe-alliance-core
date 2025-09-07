DEPENDS += "${PYTHON_PN}"

SRCREV:pn-${PN} ?= "${AUTOREV}"

SRC_URI="git://github.com/OpenSPA/SpaOpenPlugins;protocol=https;branch=8.4"

PACKAGES = "${PN}"

inherit gitpkgv gettext

python do_translate() {
    import glob
    import os

    workdir = d.getVar('S')
    folder = d.getVar('FOLDER')

    s = os.path.join(workdir,'git',folder,'plugin','locale')
    lang_domains = glob.glob(os.path.join(s, '*.pot'))
    if len(lang_domains):
        for lang in os.listdir(s):
            if lang.endswith('.po'):
                src = os.path.join(s, lang)
                lang = lang[:-3]
                destdir = os.path.join(s, lang, 'LC_MESSAGES')
                if not os.path.exists(destdir):
                    os.makedirs(destdir)
                for lang_domain in lang_domains:
                    lang_domain = lang_domain.rsplit('/', 1)[1]
                    dest = os.path.join(destdir, lang_domain[:-3] + 'mo')
                    print("Language compile %s -> %s" % (src, dest))
                    if os.system("msgfmt '%s' -o '%s'" % (src, dest)) != 0:
                        raise Exception("Failed to compile: " + src)
                os.remove(src)
    else:
        print("we got no domain -> no translation was compiled")
}
