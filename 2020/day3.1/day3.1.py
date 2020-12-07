#!/usr/bin/python

def walk_the_forest(x_coord, y_coord, the_forest):
    if y_coord > pattern_height - 1:
        return 0
    if x_coord > pattern_width - 1:
        # wrap around to follow the pattern.
        x_coord = x_coord - pattern_width

    tree_encountered = 0
    # Check the space at right_steps, down_steps
    the_tree_row = the_forest[y_coord]
    # print('  the_tree_row = {0} '.format(the_tree_row))
    the_spot = the_tree_row[x_coord]
    if the_spot == '#':
        tree_encountered = 1
    #    print('  X')
    else:
        tree_encountered = 0
    #    print('  O')
    return tree_encountered + walk_the_forest(x_coord + 3, y_coord + 1, the_forest)


print('script starting:')
with open('input') as tree_file:
    forest = {}
    pattern_width = 0
    pattern_height = 0
    for line in tree_file:
        row = line.rstrip()
        print('Reading line {0}'.format(pattern_height))
        # Set/warn if the pattern_width needs updated.
        if pattern_width != len(row):
            pattern_width = len(row)
            print('setting pattern width: {0}'.format(pattern_width))
        row_trees = []
        for c in row:
            row_trees.append(c)
        # print('row_trees = {0}'.format(row_trees))
        forest[pattern_height] = row_trees
        pattern_height = pattern_height + 1

    print('Forest pattern load complete.')
    tree_count = walk_the_forest(3, 1, forest)
    print('tree count: {0}'.format(tree_count))

print('script complete!!')
