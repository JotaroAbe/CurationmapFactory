# CurationmapFactory
## はじめに
このリポジトリは、[キュレーションマップシステム](https://github.com/JotaroAbe/CurationmapGenerator)の一部です。
## 概要
キュレーションマップのデータを生成し、データベースに挿入するためのモジュールです。検索エンジンを用いて文書を収集し解析を行ったあと、マップを生成します。
## 処理
1. Bing Search(Google Custom Search)APIを用いて文書を収集
2. 文書ごとに解析(形態素等)、テキスト断片への分割
3. テキスト断片と文書間のリンク生成
4. テキスト断片、リンクの併合
5. まとめ文書推定計算(HITS)
6. リンク先を文書内テキスト断片への変更
7. データベースに挿入
## language, library, Format
- Scala
- Json
- [jsoup](https://jsoup.org/)
- [FelisCatusZero](https://github.com/ktr-skmt/FelisCatusZero-multilingual)
