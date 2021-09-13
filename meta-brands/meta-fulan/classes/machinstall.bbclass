oe_machinstall() {
    # Purpose: Install machine dependent files, if available
    #          If not available, check if there is a default
    #          If no default, just touch the destination
    # Example:
    #                $1  $2   $3         $4
    # oe_machinstall -m 0644 fstab ${D}/etc/fstab
    #
    # TODO: Check argument number?
    #
    filename=`basename $3`
    dirname=`dirname $3`

    for o in `echo ${OVERRIDES} | tr ':' ' '`; do
        if [ -e $dirname/$o/$filename ]; then
            bbnote $dirname/$o/$filename present, installing to $4
            install $1 $2 $dirname/$o/$filename $4
            return
        fi
    done
#   bbnote overrides specific file NOT present, trying default=$3...
    if [ -e $3 ]; then
        bbnote $3 present, installing to $4
        install $1 $2 $3 $4
    else
        bbnote $3 NOT present, touching empty $4
    touch $4
    fi
}
