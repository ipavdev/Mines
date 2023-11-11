# Mines
Simple Mine Plugin, you'll need to setup the sql database.

Create a database called "mines" then execute:

```
CREATE TABLE `userdata` (
  `uuid` text NOT NULL,
  `stoneAmount` int(11) NOT NULL DEFAULT 100,
  `ironAmount` int(11) NOT NULL DEFAULT 0,
  `goldAmount` int(11) NOT NULL DEFAULT 0,
  `diamondAmount` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

Coordinates are hard coded for reasons.
