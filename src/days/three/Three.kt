


/*
const solver1 = test => test
    .matchAll(/mul\(\d+,\d+\)/gm)
    .toArray().flatMap(r => r[0])
    .map(r => r.matchAll(/\d+/gm).toArray()
    .flatMap(rr => parseInt(rr)))
    .map(a => a[0] * a[1])
    .reduce((a,c) => a+c,0)
 */