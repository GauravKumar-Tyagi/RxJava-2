# RxJava-2
RxJava 2 - How to use RxJava 2 in Android

Operators Covered :

Map : transform the items emitted by an Observable by applying a function to each item

Zip : combine the emissions of multiple Observables together via a specified function and emit single items for each combination based on the results of this function

Filter : emit only those items from an Observable that pass a predicate test

FlatMap : transform the items emitted by an Observable into Observables, then flatten the emissions from those into a single Observable

Take : emit only the first n items emitted by an Observable

Reduce : apply a function to each item emitted by an Observable, sequentially, and emit the final value

Skip : suppress the first n items emitted by an Observable

Buffer : periodically gather items emitted by an Observable into bundles and emit these bundles rather than emitting the items one at a time

Concat : emit the emissions from two or more Observables without interleaving them

Replay : ensure that all observers see the same sequence of emitted items, even if they subscribe after the Observable has begun emitting items

Merge : combine multiple Observables into one by merging their emissions

SwitchMap : transform the items emitted by an Observable into Observables, and mirror those items emitted by the most-recently transformed Observable

Much more.....
