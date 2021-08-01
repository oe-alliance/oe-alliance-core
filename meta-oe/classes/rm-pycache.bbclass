python rm_pycache (){
    import shutil
    installdir = os.path.dirname(d.getVar("D"))

    for i in os.walk(installdir, topdown=False):
        if i[0].split('/')[-1] == "__pycache__":
            shutil.rmtree(i[0])
}


do_install[postfuncs] += "${RMPYCACHE} "
RMPYCACHE:class-target = " rm_pycache "
RMPYCACHE:class-nativesdk = " rm_pycache "
RMPYCACHE = ""