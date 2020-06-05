from os import path

master = {}
with open(path.join(path.dirname(__file__), "100k.txt"), "rU") as f:
	full_dict = [i.lower() for i in f.read().split("\n") if len(i) > 0] #tk there's some interesting stuff to find out the optimal dict size.

with open("extra_words.txt", "rU") as f:
	full_dict = [i.lower() for i in f.read().split("\n") if len(i) > 0] + full_dict
d = full_dict[:26000]
#~ d = full_dict[:40000]

def gen_pattern(word):
	word = word.lower()
	r = ""
	alphabet = map(chr, range(ord('a'), ord('z') + 1))
	charmap = {}
	for char in word:
		if char not in charmap:
			charmap[char] = alphabet[0]
			alphabet = alphabet[1:]
		r += charmap[char]
	return r
		

for word in d:
	p = gen_pattern(word)
	if p not in master:
		master[p] = [word]
	else:
		master[p].append(word)
	

for key in master:
	with open(path.join("patterns", key + ".txt"), "w") as f:
		f.write("\n".join(master[key]))
