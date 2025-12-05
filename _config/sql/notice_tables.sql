-- ----------------------------
-- Table structure for pay_notice
-- Platform notice table
-- ----------------------------
DROP TABLE IF EXISTS pay_notice;
CREATE TABLE pay_notice (
    id int8 NOT NULL,
    title varchar(200) COLLATE pg_catalog.default NOT NULL,
    content text COLLATE pg_catalog.default,
    target_type varchar(20) COLLATE pg_catalog.default NOT NULL,
    target_mch_nos text COLLATE pg_catalog.default,
    status varchar(20) COLLATE pg_catalog.default NOT NULL,
    publish_time timestamp(6),
    publisher_id int8,
    sort_no int4,
    pinned bool,
    creator int8,
    create_time timestamp(6),
    last_modifier int8,
    last_modified_time timestamp(6),
    version int4 NOT NULL DEFAULT 0,
    deleted bool NOT NULL DEFAULT false
);
COMMENT ON COLUMN pay_notice.id IS 'Primary key';
COMMENT ON COLUMN pay_notice.title IS 'Notice title';
COMMENT ON COLUMN pay_notice.content IS 'Notice content';
COMMENT ON COLUMN pay_notice.target_type IS 'Target type: all or specific';
COMMENT ON COLUMN pay_notice.target_mch_nos IS 'Target merchant numbers, comma separated';
COMMENT ON COLUMN pay_notice.status IS 'Notice status: draft, published, disabled';
COMMENT ON COLUMN pay_notice.publish_time IS 'Publish time';
COMMENT ON COLUMN pay_notice.publisher_id IS 'Publisher user id';
COMMENT ON COLUMN pay_notice.sort_no IS 'Sort order';
COMMENT ON COLUMN pay_notice.pinned IS 'Whether pinned';
COMMENT ON COLUMN pay_notice.creator IS 'Creator ID';
COMMENT ON COLUMN pay_notice.create_time IS 'Create time';
COMMENT ON COLUMN pay_notice.last_modifier IS 'Last modifier ID';
COMMENT ON COLUMN pay_notice.last_modified_time IS 'Last modified time';
COMMENT ON COLUMN pay_notice.version IS 'Version number';
COMMENT ON COLUMN pay_notice.deleted IS 'Deleted flag';
COMMENT ON TABLE pay_notice IS 'Platform notice table';

-- Primary key constraint
ALTER TABLE pay_notice ADD CONSTRAINT pk_pay_notice PRIMARY KEY (id);

-- Index for status query
CREATE INDEX idx_pay_notice_status ON pay_notice(status);

-- Index for target type query
CREATE INDEX idx_pay_notice_target_type ON pay_notice(target_type);


-- ----------------------------
-- Table structure for pay_notice_read
-- Notice read record table
-- ----------------------------
DROP TABLE IF EXISTS pay_notice_read;
CREATE TABLE pay_notice_read (
    id int8 NOT NULL,
    notice_id int8 NOT NULL,
    mch_no varchar(32) COLLATE pg_catalog.default NOT NULL,
    user_id int8 NOT NULL,
    creator int8,
    create_time timestamp(6)
);
COMMENT ON COLUMN pay_notice_read.id IS 'Primary key';
COMMENT ON COLUMN pay_notice_read.notice_id IS 'Notice ID';
COMMENT ON COLUMN pay_notice_read.mch_no IS 'Merchant number';
COMMENT ON COLUMN pay_notice_read.user_id IS 'User ID who read the notice';
COMMENT ON COLUMN pay_notice_read.creator IS 'Creator ID';
COMMENT ON COLUMN pay_notice_read.create_time IS 'Create time';
COMMENT ON TABLE pay_notice_read IS 'Notice read record table';

-- Primary key constraint
ALTER TABLE pay_notice_read ADD CONSTRAINT pk_pay_notice_read PRIMARY KEY (id);

-- Unique constraint to prevent duplicate read records
CREATE UNIQUE INDEX idx_pay_notice_read_unique ON pay_notice_read(notice_id, user_id);

-- Index for notice_id query
CREATE INDEX idx_pay_notice_read_notice_id ON pay_notice_read(notice_id);

-- Index for mch_no query
CREATE INDEX idx_pay_notice_read_mch_no ON pay_notice_read(mch_no);

-- Index for user_id query
CREATE INDEX idx_pay_notice_read_user_id ON pay_notice_read(user_id);
