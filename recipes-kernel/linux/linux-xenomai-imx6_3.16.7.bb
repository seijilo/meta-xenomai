require recipes-kernel/linux/linux-dtb.inc

SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
DESCRIPTION = "Linux kernel with Xenomai patch"

inherit kernel siteinfo

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Linux kernel
SRC_URI[kernel.md5sum] = "c4ecf1836294aa39ac8cfae9db9da2d9"
SRC_URI[kernel.sha256sum] = "7bb9dac1f58c30b5916780d6596a55fcf5ad871400df1fe51db98a155ee83e00"

SRC_URI += "http://www.kernel.org/pub/linux/kernel/v3.x/linux-3.16.7.tar.gz;name=kernel"

#xenomai source (prepare_kernel.sh script)
SRC_URI += "http://xenomai.org/downloads/xenomai/testing/xenomai-3.0-rc4.tar.bz2;name=xeno"

SRC_URI[xeno.md5sum] = "281790d044007ddbfc62a38a51520dd5"
SRC_URI[xeno.sha256sum] = "349d257ace595b07edb3bea4f90abb2b8bdbcfc15463c99739b6d7bdad499610"

#kernel defconfig
SRC_URI +=  "file://defconfig"

do_prepare_kernel () {
    #set linux kernel source directory
    linux_src="${S}"

    #set xenomai source directory 
    xenomai_src="${TMPDIR}/work/${MACHINE_ARCH}-poky-${TARGET_OS}/${PN}/${EXTENDPE}${PV}-${PR}/xenomai-3.0-rc4/"

    #prepare kernel
    $xenomai_src/scripts/prepare-kernel.sh --arch=arm --linux=$linux_src --default
}

addtask prepare_kernel after do_patch before do_configure

S = "${WORKDIR}/linux-3.16.7"
