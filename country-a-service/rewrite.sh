git apply < fix.patch
mvn rewrite:run
git apply < fix_revert.patch
