require recipes-kernel/linux/linux-dtb.inc

SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
DESCRIPTION = "Linux kernel with Xenomai patch"

inherit kernel siteinfo

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

#download linux src tree
SRCREV = "3.8.13-bone67"
SRC_URI += "git://github.com/beagleboard/linux.git;protocol=git;nobranch=1"

#xenomai source (for the prepare_kernel.sh script)
SRC_URI[md5sum] = "9f83c39cfb10535df6bf51702714e716"
SRC_URI[sha256sum] = "4d0d09431f0340cf4c9e2745177f77f15b7b124f89982035d1d3586519d7afe9"

SRC_URI += "http://download.gna.org/xenomai/stable/xenomai-2.6.3.tar.bz2"

#Kernel patches for xenomai / BBB
SRC_URI +=  "file://ipipe-core-3.8.13-beaglebone-pre.patch;md5=f944db650b4f98ac4bc9e8874235f95b"
SRC_URI +=  "file://ipipe-core-3.8.13-arm-3.patch;md5=99f1bd34259199fb410d2052eb2f947d"
SRC_URI +=  "file://ipipe-core-3.8.13-beaglebone-post.patch;md5=f7c6c4c8cafbda656d68bfb81b71797d"

#kernel defconfig
SRC_URI +=  "file://defconfig"

KERNEL_IMAGETYPE = "zImage"

COMPATIBLE_MACHINE = "beaglebone"

RDEPENDS_kernel_base += "kernel-devicetree"

KERNEL_DEVICETREE_beaglebone = "am335x-boneblack.dtb"

do_prepare_kernel () {
    #set linux kernel source directory
    linux_src="${S}"

    #set xenomai source directory 
    xenomai_src="${TMPDIR}/work/${MACHINE}-poky-${TARGET_OS}/${PN}/${EXTENDPE}${PV}-${PR}/xenomai-2.6.3/"

    #prepare kernel
    $xenomai_src/scripts/prepare-kernel.sh --arch=arm --linux=$linux_src
}

addtask prepare_kernel after do_patch before do_configure

S = "${WORKDIR}/git"
