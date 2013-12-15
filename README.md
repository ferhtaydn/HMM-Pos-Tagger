HMM-Pos-Tagger
==============

Hidden Markov Model (HMM) based Part-of-Speech (POS) tagger for the biomedical domain. 
The training (genia-train.txt) and test sets (genia-test.txt), which are obtained from the Genia Corpus1

The training set contains 13677 sentences, and the test set contains 6869 sentences. 
The training and test set files contain one token/POS pair per line, and a ========== line (ten equal signs) is put between sentences.

Viterbi algorithm is used for decoding the test set.

Also, baseline tagger (that assigns the most frequent tag to each word) is implemented.

Accuracy of both viterbi and baseline is calculated.




1 http://www.nactem.ac.uk/genia/genia-corpus/pos-annotation
