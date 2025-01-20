
<style>
  body {
    font-family: sans-serif;
  }
  table {
    border-collapse: collapse;
    width: 100%;
    table-layout: fixed;
  }
  tbody {
/*     max-height: 800px; */
    height: 500px;
    overflow: scroll;
  }
  th, td {
    border: 1px solid black;
    padding: 8px;
    text-align: left;
    overflow-wrap: anywhere;
    
  }
  th {
    background-color: #f0f0f0;
    position: sticky;
    top: 0;
  }
  
  .sticky-header {
      position: sticky;
      top: 0;
      background-color: #e0e0e0;
      padding: 10px;
      font-size: 1.5em;
      z-index: 2;
  }

  td:nth-child(1) {
    width: 150px;
    position: sticky;
    left: 0;
    background-color: #f8f8f8;
    z-index: 1;
  }

  th:nth-child(1) {
    z-index: 3;
  }
  
  a {
      text-decoration: none;
  }
  
  td:nth-child(10) {
    width: 150px;
    position: sticky;
    right: 0;
    background-color: #f8f8f8;
    z-index: 1;
  }

  th:nth-child(10) {
    z-index: 3;
  }
</style>

<div class="sticky-header">Powerseq vs. Other Libraries</div>

<table>
  <thead>
    <tr>
      <th>powerseq</th>
      <th><a href="https://msdn.microsoft.com/en-us/library/system.linq.enumerable(v=vs.110).aspx">LINQ</a></th>
      <th><a href="http://reactivex.io/rxjs/class/es6/Observable.js~Observable.html">RxJS</a></th>
      <th><a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array">JS Array</a></th>
      <th><a href="https://lodash.com/docs/4.17.2">lodash</a></th>
      <th><a href="https://fsharp.github.io/fsharp-core-docs/reference/fsharp-collections-seqmodule.html">F#</a></th>
      <th><a href="https://clojure.org/api/cheatsheet">Clojure</a></th>
      <th><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/-sequence/">Kotlin</a></th>
      <th><a href="https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/stream/Stream.html">Java</a></th>
      <th>powerseq</th>
    </tr>
  </thead>
  <tbody>
    <tr><td>average</td><td>Average</td><td></td><td>mean<br>meanBy</td><td>average<br>averageBy</td><td></td><td>average</td><td>C.averaging*<br>C.summarizing*</td><td>average</td></tr>
    <tr><td>buffer</td><td>Chunk</td><td>bufferCount<br>pairwise<br><br></td><td></td><td>chunk</td><td>chunkBySize<br>windowed<br>pairwise</td><td>partition-all<br>partition~<br><br></td><td>chunked<br>windowed<br><br></td><td></td><td>buffer</td></tr>
    <tr><td>cast</td><td>Cast</td><td></td><td></td><td></td><td>cast</td><td></td><td></td><td></td><td>cast</td></tr>
    <tr><td>combinations</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>combinations</td></tr>
    <tr><td>concat</td><td>Concat</td><td>concat</td><td>concat</td><td>concat</td><td>append</td><td>concat</td><td>plus<br>plusElement</td><td>concat</td><td>concat</td></tr>
    <tr><td>count</td><td>Count</td><td>count</td><td></td><td>size</td><td>length</td><td>count</td><td>count</td><td>count<br>C.counting<br>C.summarizing*</td><td>count</td></tr>
    <tr><td>countby</td><td></td><td></td><td></td><td></td><td>countBy</td><td></td><td></td><td></td><td>countby</td></tr>
    <tr><td>defaultifempty</td><td>DefaultIfEmpty</td><td>defaultIfEmpty</td><td></td><td></td><td></td><td></td><td>ifEmpty</td><td></td><td>defaultifempty</td></tr>
    <tr><td>defer</td><td></td><td>defer</td><td></td><td></td><td>delay</td><td></td><td></td><td></td><td>defer</td></tr>
    <tr><td>distinct</td><td>Distinct</td><td>distinct</td><td></td><td>uniq<br>uniqWith</td><td>distinct</td><td>distinct</td><td>distinct</td><td>distinct</td><td>distinct</td></tr>
    <tr><td>distinctby</td><td>DistinctBy</td><td></td><td></td><td>uniqBy</td><td>distinctBy</td><td></td><td>distinctBy</td><td></td><td>distinctby</td></tr>
    <tr><td>distinctuntilchanged</td><td></td><td>distinctUntilChanged<br>distinctUntilKeyChanged</td><td></td><td></td><td></td><td>dedupe</td><td></td><td></td><td>distinctuntilchanged</td></tr>
    <tr><td>doo</td><td></td><td>do</td><td></td><td></td><td></td><td></td><td>onEach</td><td>peek</td><td>doo</td></tr>
    <tr><td>elementat</td><td>ElementAt<br>ElementAtOrDefault<br><br></td><td>elementAt</td><td></td><td>nth</td><td>nth</td><td>nth<br>get~<br><br></td><td>elementAt<br>elementAtOrElse<br>elementAtOrNull</td><td></td><td>elementat</td></tr>
    <tr><td>empty</td><td></td><td>empty</td><td></td><td></td><td>empty</td><td></td><td>emptySequence</td><td>empty</td><td>empty</td></tr>
    <tr><td>entries</td><td></td><td>pairs</td><td>entries</td><td></td><td></td><td></td><td></td><td></td><td>entries</td></tr>
    <tr><td>every</td><td>All</td><td>every</td><td>every</td><td>every</td><td>forall</td><td>every?</td><td>all</td><td>allMatch</td><td>every</td></tr>
    <tr><td>except</td><td>Except</td><td></td><td></td><td>difference<br>without</td><td></td><td>difference~</td><td>minus~</td><td></td><td>except</td></tr>
    <tr><td>exceptby</td><td>ExceptBy</td><td></td><td></td><td>differenceBy<br>differenceWith</td><td></td><td></td><td></td><td></td><td>exceptby</td></tr>
    <tr><td>expand</td><td></td><td>expand</td><td></td><td></td><td>unfold</td><td></td><td></td><td></td><td>expand</td></tr>
    <tr><td>filter</td><td>Where</td><td>filter</td><td>filter</td><td>filter</td><td>filter<br>where</td><td>filter</td><td>filter<br>filterIndexed</td><td>filter</td><td>filter</td></tr>
    <tr><td>filtermap</td><td></td><td></td><td></td><td></td><td>choose</td><td>keep</td><td>mapNotNull<br>mapIndexedNotNull</td><td></td><td>filtermap</td></tr>
    <tr><td>find</td><td>First<br>FirstOrDefault<br><br></td><td>find<br>first<br><br></td><td>find</td><td>first<br>head<br>find</td><td>find~<br>tryFind<br>head</td><td>first~</td><td>find<br>first~<br><br></td><td>findFirst~</td><td>find</td></tr>
    <tr><td>findindex</td><td></td><td>findIndex</td><td>findIndex</td><td>findIndex</td><td>findIndex~<br>tryFindIndex</td><td></td><td>indexOfFirst<br>indexOf~</td><td></td><td>findindex</td></tr>
    <tr><td>flat</td><td></td><td></td><td>flat~</td><td>flatten</td><td></td><td>flatten~</td><td>flatten</td><td></td><td>flat</td></tr>
    <tr><td>flatmap</td><td>SelectMany</td><td></td><td>flatMap</td><td>flatMap</td><td>collect</td><td>mapcat</td><td>flatMap</td><td>flatMap<br>mapMulti</td><td>flatmap</td></tr>
    <tr><td>foreach</td><td></td><td></td><td>forEach</td><td>each<br>forEach</td><td>iter<br>iteri</td><td></td><td>forEach<br>forEachIndexed</td><td>forEach</td><td>foreach</td></tr>
    <tr><td>generate</td><td></td><td></td><td></td><td></td><td>init<br>initInfinite</td><td>iterate<br>repeatedly</td><td>generate</td><td>iterate</td><td>generate</td></tr>
    <tr><td>groupby</td><td>GroupBy</td><td>groupBy</td><td></td><td>groupBy</td><td>groupBy</td><td>group-by</td><td>groupBy<br>groupingBy</td><td>C.groupingBy</td><td>groupby</td></tr>
    <tr><td>groupbytoobject</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>groupbytoobject</td></tr>
    <tr><td>groupbytotransformedobject</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>groupbytotransformedobject</td></tr>
    <tr><td>groupjoin</td><td>GroupJoin</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>groupjoin</td></tr>
    <tr><td>ignoreelements</td><td></td><td>ignoreElements</td><td></td><td></td><td></td><td></td><td></td><td></td><td>ignoreelements</td></tr>
    <tr><td>includes</td><td>Contains</td><td></td><td>includes</td><td>includes</td><td>contains</td><td>contains?</td><td>contains</td><td></td><td>includes</td></tr>
    <tr><td>interleave</td><td></td><td></td><td></td><td></td><td></td><td>interleave</td><td></td><td></td><td>interleave</td></tr>
    <tr><td>interpose</td><td></td><td></td><td></td><td></td><td></td><td>interpose</td><td></td><td></td><td>interpose</td></tr>
    <tr><td>intersect</td><td>Intersect</td><td></td><td></td><td>intersection</td><td></td><td>intersection~</td><td>intersect~</td><td></td><td>intersect</td></tr>
    <tr><td>intersectby</td><td>IntersectBy</td><td></td><td></td><td>intersectionBy<br>intersectionWith</td><td></td><td></td><td></td><td></td><td>intersectby</td></tr>
    <tr><td>isempty</td><td></td><td>isEmpty</td><td></td><td></td><td>isEmpty</td><td>empty?</td><td>none</td><td></td><td>isempty</td></tr>
    <tr><td>join</td><td>Join</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>join</td></tr>
    <tr><td>last</td><td>Last<br>LastOrDefault</td><td>last</td><td></td><td>findLast</td><td>last</td><td></td><td>findLast<br>last~</td><td></td><td>last</td></tr>
    <tr><td>map</td><td>Select</td><td>map</td><td>map</td><td>map</td><td>map<br>mapi</td><td>map</td><td>map<br>mapIndexed</td><td>map</td><td>map</td></tr>
    <tr><td>max</td><td>Max</td><td>max</td><td></td><td>max</td><td>max</td><td></td><td>maxOf</td><td>max</td><td>max</td></tr>
    <tr><td>maxby</td><td>MaxBy</td><td></td><td></td><td>maxBy</td><td>maxBy</td><td>max-key</td><td>maxBy</td><td>C.maxBy<br>C.summarizing*</td><td>maxby</td></tr>
    <tr><td>memoize</td><td></td><td></td><td></td><td></td><td>cache</td><td></td><td></td><td></td><td>memoize</td></tr>
    <tr><td>min</td><td>Min</td><td>min</td><td></td><td>min</td><td>min</td><td></td><td>minOf</td><td>min</td><td>min</td></tr>
    <tr><td>minby</td><td>MinBy</td><td></td><td></td><td>minBy</td><td>minBy</td><td>min-key</td><td>minBy</td><td>C.minBy<br>C.summarizing*</td><td>minby</td></tr>
    <tr><td>oftype</td><td>OfType</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>oftype</td></tr>
    <tr><td>orderby</td><td>OrderBy</td><td></td><td>sort</td><td>orderBy<br>sortBy</td><td>sort<br>sortBy</td><td>sort<br>sort-by</td><td>sorted<br>sortedBy</td><td>sorted</td><td>orderby</td></tr>
    <tr><td>orderbydescending</td><td>OrderByDescending</td><td></td><td>sort</td><td>orderBy<br>sortBy</td><td>sort<br>sortBy</td><td></td><td>sortedDescending<br>sortedByDescending</td><td></td><td>orderbydescending</td></tr>
    <tr><td>pairwise</td><td></td><td>pairwise</td><td></td><td></td><td>pairwise</td><td></td><td></td><td></td><td>pairwise</td></tr>
    <tr><td>partitionby</td><td></td><td></td><td></td><td></td><td></td><td>partition-by</td><td></td><td></td><td>partitionby</td></tr>
    <tr><td>range</td><td>Range</td><td>range</td><td></td><td>range</td><td></td><td>range</td><td></td><td></td><td>range</td></tr>
    <tr><td>reduce</td><td>Aggregate</td><td>reduce</td><td>reduce</td><td>reduce</td><td>fold<br>reduce<br><br></td><td>reduce</td><td>fold<br>reduce<br>reduceOrNull</td><td>reduce</td><td>reduce</td></tr>
    <tr><td>repeat</td><td></td><td>repeat</td><td></td><td></td><td></td><td>cycle</td><td></td><td></td><td>repeat</td></tr>
    <tr><td>repeatvalue</td><td></td><td></td><td></td><td></td><td></td><td>repeat</td><td></td><td></td><td>repeatvalue</td></tr>
    <tr><td>reverse</td><td>Reverse</td><td></td><td>reverse</td><td>reverse</td><td></td><td>reverse</td><td></td><td></td><td>reverse</td></tr>
    <tr><td>scan</td><td></td><td>scan</td><td></td><td></td><td>scan</td><td>reductions</td><td>scan<br>runningFold<br>runningReduce</td><td></td><td>scan</td></tr>
    <tr><td>sequenceequal</td><td>SequenceEqual</td><td>sequenceEqual</td><td></td><td></td><td></td><td></td><td></td><td></td><td>sequenceequal</td></tr>
    <tr><td>share</td><td></td><td>share</td><td></td><td></td><td></td><td></td><td></td><td></td><td>share</td></tr>
    <tr><td>single</td><td>Single<br>SingleOrDefault</td><td>single</td><td></td><td></td><td>exactlyOne</td><td></td><td>single<br>singleOrNull</td><td></td><td>single</td></tr>
    <tr><td>skip</td><td>Skip</td><td>skip</td><td></td><td>drop<br>tail</td><td>skip~</td><td>drop</td><td>drop</td><td>skip</td><td>skip</td></tr>
    <tr><td>skiplast</td><td></td><td></td><td></td><td>dropRight<br>initial</td><td></td><td>drop-last</td><td></td><td></td><td>skiplast</td></tr>
    <tr><td>skipwhile</td><td>SkipWhile</td><td>skipWhile</td><td></td><td>dropWhile</td><td>skipWhile</td><td>drop-while</td><td>dropWhile</td><td>dropwhile</td><td>skipwhile</td></tr>
    <tr><td>some</td><td>Any</td><td></td><td>some</td><td>some</td><td>exists</td><td>some</td><td>any</td><td>anyMatch</td><td>some</td></tr>
    <tr><td>sum</td><td>Sum</td><td></td><td></td><td>sum<br>sumBy</td><td>sum<br>sumBy</td><td></td><td>sum<br>sumOf</td><td>C.summing*<br>C.summarizing*</td><td>sum</td></tr>
    <tr><td>take</td><td>Take</td><td>take</td><td></td><td>take</td><td>truncate<br>~take</td><td>take</td><td>take</td><td>limit</td><td>take</td></tr>
    <tr><td>takelast</td><td></td><td>takeLast</td><td></td><td>last<br>takeRight</td><td></td><td>take-last</td><td></td><td></td><td>takelast</td></tr>
    <tr><td>takewhile</td><td>TakeWhile</td><td>takeWhile</td><td></td><td>takeWhile</td><td>takeWhile</td><td>take-while</td><td>takeWhile</td><td>takeWhile</td><td>takewhile</td></tr>
    <tr><td>thenby</td><td>ThenBy</td><td></td><td>sort</td><td>orderBy<br>sortBy</td><td>sort<br>sortBy</td><td></td><td></td><td></td><td>thenby</td></tr>
    <tr><td>thenbydescending</td><td>ThenByDescending</td><td></td><td>sort</td><td>orderBy<br>sortBy</td><td>sort<br>sortBy</td><td></td><td></td><td></td><td>thenbydescending</td></tr>
    <tr><td>throww</td><td></td><td>throw</td><td></td><td></td><td></td><td></td><td></td><td></td><td>throww</td></tr>
    <tr><td>toarray</td><td>ToArray</td><td></td><td></td><td></td><td>toArray</td><td></td><td>toList</td><td>toArray<br>toList<br>C.toList</td><td>toarray</td></tr>
    <tr><td>tomap</td><td>ToDictionary</td><td></td><td></td><td></td><td></td><td></td><td>toMap</td><td>C.toMap</td><td>tomap</td></tr>
    <tr><td>toobject</td><td></td><td></td><td></td><td>fromPairs<br>keyBy<br><br></td><td></td><td></td><td>associate<br>associateBy<br>associateWith</td><td>C.toMap</td><td>toobject</td></tr>
    <tr><td>union</td><td>Union</td><td></td><td></td><td>union</td><td></td><td>union~</td><td>union~</td><td></td><td>union</td></tr>
    <tr><td>unionby</td><td>UnionBy</td><td></td><td></td><td>unionBy<br>unionWith</td><td></td><td></td><td></td><td></td><td>unionby</td></tr>
    <tr><td>zip</td><td>Zip</td><td>zip</td><td></td><td>zip<br>zip3</td><td>map</td><td>zip</td><td></td><td>zip</td><td></td></tr>
  </tbody>
</table>

